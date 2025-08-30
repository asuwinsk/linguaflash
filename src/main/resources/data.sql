SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE languages;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO languages (code, name) VALUES
    ('en', 'English'),
    ('pl', 'Polish'),
    ('de', 'German'),
    ('fr', 'French'),
    ('es', 'Spanish');

# INSERT INTO flashcards(back, front, level, example_sentence) VALUES
#     ('Hello', 'Cześć', 'A1', 'Hello! How are you?'),
#     ('Goodbye', 'Do widzenia', 'A1', 'Goodbye! See you tomorrow.'),
#     ('Please', 'Proszę', 'A1', 'Please, can you help me?'),
#     ('Thank you', 'Dziękuję', 'A1', 'Thank you for your help.'),
#     ('Yes', 'Tak', 'A1', 'Yes, I would like some coffee.');
#
# INSERT INTO decks (id, name, description, date_created, color) VALUES
#     (1, 'Basic English', 'Deck for basic English words', '2024-06-01'),
#     (2, 'German Starter', 'Starter deck for German learners', '2024-06-02'),
#     (3, 'French Phrases', 'Common French phrases', '2024-06-03');
#
# INSERT INTO deck_tags VALUES
#     (1, 'Beginner'),
#     (1, 'Common Phrases'),
#     (2, 'Beginner'),
#     (3, 'Travel');
