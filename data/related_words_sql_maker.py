import gensim
import nltk
from itertools import chain

model = gensim.models.Word2Vec.load("scpword2vec.model") # add model path here

class RelatedWordsSQLMaker:
    def __init__(self, model):
        self.model =  model
        self.related_words = {}
        self._get_all_words()

    def _get_all_words(self):
        self.all_words = list([i for i in self.model.wv.index_to_key if i[0].isalpha() and "_" not in i])[:5000]

    def get_related_words(self, word, n=20):
        self.related_words[word] = [i[0] for i in self.model.wv.most_similar(word, topn=n+10) if i[0].isalpha() and "_" not in i][:n]

    def get_all_related_words(self):
        for word in self.all_words:
            self.get_related_words(word)
        return self.related_words

    def make_sql_word_entry(self, word):
        insert_statement = "INSERT INTO words (text_value, part_of_speech, length) VALUES ('{}', '{}', {});\n".format(word,
        self._get_part_of_speech(word), len(word))
        return insert_statement

    def make_write_all_sql_word_entries(self, filename):
        file_string = ""
        for word in self.all_words:
            statement = self.make_sql_word_entry(word)
            file_string += statement
        file = open(f"{filename}.sql" , "w")
        file.write(file_string)
        file.close()

    def _get_part_of_speech(self, word):
        tag = nltk.pos_tag(list(word))[0][1]

        if tag.startswith("NN"): return "NOUN"
        elif tag.startswith("JJ"): return "ADJECTIVE"
        elif tag.startswith("V"): return "VERB"
        elif tag.startswith("RB"): return "ADVERB"
        else: return "NULL"

    def make_sql_related_words(self, word):
        if not self.related_words:
            self.get_all_related_words()
        related_words = self.related_words.get(word)
        # print(related_words)
        related_word_statements = []
        for related_word in related_words:
            if related_word not in self.all_words:
                continue
            insert_statement = f"INSERT INTO semantic_similar (word1_id, word2_id) VALUES ({self.all_words.index(word)}, {self.all_words.index(related_word)});\n"
            related_word_statements.append(insert_statement)
        return related_word_statements

    def make_write_all_sql_related_words(self, filename):
        all_statements = []
        for word in self.all_words:
            statements = self.make_sql_related_words(word)
            all_statements.append(statements)
        statements = "".join(chain.from_iterable(all_statements))
        file = open(f"{filename}.sql" , "w")
        file.write(statements)
        file.close()


if __name__ == "__main__":
    sql_maker = RelatedWordsSQLMaker(model)
    sql_maker.make_write_all_sql_word_entries("V2_2__")
    sql_maker.make_write_all_sql_related_words("V3_3__")
