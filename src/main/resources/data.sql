SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE languages;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO languages (code, name) VALUES
    ('en', 'English'),
    ('pl', 'Polish'),
    ('de', 'German'),
    ('fr', 'French'),
    ('es', 'Spanish'),
    ('zh', 'Chinese');


SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE translations;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO translations (source_text, source_lang, target_text, target_lang)
VALUES
    -- EN ↔ PL
    ('Hello', 'en', 'Cześć', 'pl'),
    ('World', 'en', 'Świat', 'pl'),
    ('Dog', 'en', 'Pies', 'pl'),
    ('Cat', 'en', 'Kot', 'pl'),
    ('One', 'en', 'Jeden', 'pl'),
    ('Two', 'en', 'Dwa', 'pl'),
    ('Red', 'en', 'Czerwony', 'pl'),
    ('Blue', 'en', 'Niebieski', 'pl'),
    ('Eat', 'en', 'Jeść', 'pl'),
    ('Drink', 'en', 'Pić', 'pl'),
    ('Cześć', 'pl', 'Hello', 'en'),
    ('Świat', 'pl', 'World', 'en'),
    ('Kot', 'pl', 'Cat', 'en'),
    ('Pies', 'pl', 'Dog', 'en'),
    ('Jeden', 'pl', 'One', 'en'),
    ('Dwa', 'pl', 'Two', 'en'),
    ('Czerwony', 'pl', 'Red', 'en'),
    ('Niebieski', 'pl', 'Blue', 'en'),
    ('Jeść', 'pl', 'Eat', 'en'),
    ('Pić', 'pl', 'Drink', 'en'),

    -- EN ↔ DE
    ('Hello', 'en', 'Hallo', 'de'),
    ('World', 'en', 'Welt', 'de'),
    ('Dog', 'en', 'Hund', 'de'),
    ('Cat', 'en', 'Katze', 'de'),
    ('One', 'en', 'Eins', 'de'),
    ('Two', 'en', 'Zwei', 'de'),
    ('Red', 'en', 'Rot', 'de'),
    ('Blue', 'en', 'Blau', 'de'),
    ('Eat', 'en', 'Essen', 'de'),
    ('Drink', 'en', 'Trinken', 'de'),
    ('Hallo', 'de', 'Hello', 'en'),
    ('Welt', 'de', 'World', 'en'),
    ('Katze', 'de', 'Cat', 'en'),
    ('Hund', 'de', 'Dog', 'en'),
    ('Eins', 'de', 'One', 'en'),
    ('Zwei', 'de', 'Two', 'en'),
    ('Rot', 'de', 'Red', 'en'),
    ('Blau', 'de', 'Blue', 'en'),
    ('Essen', 'de', 'Eat', 'en'),
    ('Trinken', 'de', 'Drink', 'en'),

    -- EN ↔ FR
    ('Hello', 'en', 'Bonjour', 'fr'),
    ('World', 'en', 'Monde', 'fr'),
    ('Dog', 'en', 'Chien', 'fr'),
    ('Cat', 'en', 'Chat', 'fr'),
    ('One', 'en', 'Un', 'fr'),
    ('Two', 'en', 'Deux', 'fr'),
    ('Red', 'en', 'Rouge', 'fr'),
    ('Blue', 'en', 'Bleu', 'fr'),
    ('Eat', 'en', 'Manger', 'fr'),
    ('Drink', 'en', 'Boire', 'fr'),
    ('Bonjour', 'fr', 'Hello', 'en'),
    ('Monde', 'fr', 'World', 'en'),
    ('Chien', 'fr', 'Dog', 'en'),
    ('Chat', 'fr', 'Cat', 'en'),
    ('Un', 'fr', 'One', 'en'),
    ('Deux', 'fr', 'Two', 'en'),
    ('Rouge', 'fr', 'Red', 'en'),
    ('Bleu', 'fr', 'Blue', 'en'),
    ('Manger', 'fr', 'Eat', 'en'),
    ('Boire', 'fr', 'Drink', 'en'),

    -- EN ↔ ES
    ('Hello', 'en', 'Hola', 'es'),
    ('World', 'en', 'Mundo', 'es'),
    ('Dog', 'en', 'Perro', 'es'),
    ('Cat', 'en', 'Gato', 'es'),
    ('One', 'en', 'Uno', 'es'),
    ('Two', 'en', 'Dos', 'es'),
    ('Red', 'en', 'Rojo', 'es'),
    ('Blue', 'en', 'Azul', 'es'),
    ('Eat', 'en', 'Comer', 'es'),
    ('Drink', 'en', 'Beber', 'es'),
    ('Hola', 'es', 'Hello', 'en'),
    ('Mundo', 'es', 'World', 'en'),
    ('Perro', 'es', 'Dog', 'en'),
    ('Gato', 'es', 'Cat', 'en'),
    ('Uno', 'es', 'One', 'en'),
    ('Dos', 'es', 'Two', 'en'),
    ('Rojo', 'es', 'Red', 'en'),
    ('Azul', 'es', 'Blue', 'en'),
    ('Comer', 'es', 'Eat', 'en'),
    ('Beber', 'es', 'Drink', 'en'),

    -- EN ↔ ZH
    ('Hello', 'en', '你好', 'zh'),
    ('World', 'en', '世界', 'zh'),
    ('Dog', 'en', '狗', 'zh'),
    ('Cat', 'en', '猫', 'zh'),
    ('One', 'en', '一', 'zh'),
    ('Two', 'en', '二', 'zh'),
    ('Red', 'en', '红色', 'zh'),
    ('Blue', 'en', '蓝色', 'zh'),
    ('Eat', 'en', '吃', 'zh'),
    ('Drink', 'en', '喝', 'zh'),
    ('你好', 'zh', 'Hello', 'en'),
    ('世界', 'zh', 'World', 'en'),
    ('狗', 'zh', 'Dog', 'en'),
    ('猫', 'zh', 'Cat', 'en'),
    ('一', 'zh', 'One', 'en'),
    ('二', 'zh', 'Two', 'en'),
    ('红色', 'zh', 'Red', 'en'),
    ('蓝色', 'zh', 'Blue', 'en'),
    ('吃', 'zh', 'Eat', 'en'),
    ('喝', 'zh', 'Drink', 'en');

