INSERT INTO user (id, email, password, firstname, lastname) VALUES (1, 'fred@epam.com', '13132', 'Fred', 'Mokman');
INSERT INTO user (id, email, password, firstname, lastname) VALUES (2, 'ulfrick@epam.com', '4123', 'Ulfrick', 'Burefestnik');
INSERT INTO user (id, email, password, firstname, lastname) VALUES (3, 'drymin@epam.com', '3122', 'Andrey', 'Drymin');
INSERT INTO user (id, email, password, firstname, lastname) VALUES (4, 'kichev@epam.com', '41414', 'Alexander', 'Kichev');
INSERT INTO user (id, email, password, firstname, lastname) VALUES (5, 'superman@epam.com', '3232', 'Kent', 'Clark');
INSERT INTO user (id, email, password, firstname, lastname) VALUES (6, 'mestniy@epam.com', '1212', 'Leha', 'Ptica');
INSERT INTO user (id, email, password, firstname, lastname) VALUES (7, 'cocetka@epam.com', '13424', 'Janna', 'Vrotmoshnaia');

INSERT INTO notebook (id, title, user_id, description) VALUES (1, 'Food', 1, 'I need more food');
INSERT INTO notebook (id, title, user_id, description) VALUES (2, 'Weapon', 2, 'More weapon to destroy Imperial');
INSERT INTO notebook (id, title, user_id, description) VALUES (3, 'Spring', 3, 'How make pupils cleaver');
INSERT INTO notebook (id, title, user_id, description) VALUES (4, 'LAMA', 4, 'This project saks');
INSERT INTO notebook (id, title, user_id, description) VALUES (5, 'Lex', 5, 'My friend and foe in one face');
INSERT INTO notebook (id, title, user_id, description) VALUES (6, 'Semki', 6, 'I need buy more semki');
INSERT INTO notebook (id, title, user_id, description) VALUES (7, 'Pubs', 7, 'Places where i can rest');
INSERT INTO notebook (id, title, user_id, description) VALUES (8, 'Water', 1, 'I need more water');
INSERT INTO notebook (id, title, user_id, description) VALUES (9, 'Dragons', 2, 'F@ck that stupid dragons');
INSERT INTO notebook (id, title, user_id, description) VALUES (10, 'My life', 3, 'Thoughts about the eternal');
INSERT INTO notebook (id, title, user_id, description) VALUES (11, 'Tattoo', 4, 'I am love it!');
INSERT INTO notebook (id, title, user_id, description) VALUES (12, 'Job', 5, 'I mean stupid job');
INSERT INTO notebook (id, title, user_id, description) VALUES (13, 'Drags', 6, 'Better if no one will read this');
INSERT INTO notebook (id, title, user_id, description) VALUES (14, 'Mans', 7, 'This is my passion');

INSERT INTO note (id, title, body, notebook_id)
VALUES (1, 'Supermarket', 'Ohhh yeaaa', 1);
INSERT INTO note (id, title, body, notebook_id)
VALUES (2, 'Market', 'Super ohh yeaaa', 1);
INSERT INTO note (id, title, body, notebook_id)
VALUES (3, 'Garbage', 'Ohhh nooo', 1);
INSERT INTO note (id, title, body, notebook_id)
VALUES (4, 'Recruits', 'Send more recruits on search', 2);
INSERT INTO note (id, title, body, notebook_id)
VALUES (5, 'Fighting skirmish', 'After grab all weapons', 2);
INSERT INTO note (id, title, body, notebook_id)
VALUES (6, 'Shopping', 'Ughr it is expensive', 2);
INSERT INTO note (id, title, body, notebook_id)
VALUES (7, 'Guys', 'I think i can be a cool teacher', 3);
INSERT INTO note (id, title, body,  notebook_id)
VALUES (8, 'What happened', 'I need stay a team lead as soon as possible ', 4);
INSERT INTO note (id, title, body, notebook_id)
VALUES (9, 'How it is happened', 'We were friend but latter...',  5);
INSERT INTO note (id, title, body, notebook_id)
VALUES (10, 'Market', 'Buy mooooreee semki', 6);

INSERT INTO mark (id, type, user_id) VALUES (1, 'MAIN', 1);
INSERT INTO mark (id, type, user_id) VALUES (2, 'STRANGE',4);
INSERT INTO mark (id, type, user_id) VALUES (3, 'BAD', 7);
INSERT INTO mark (id, type, user_id) VALUES (4, 'LOL', 6);
INSERT INTO mark (id, type, user_id) VALUES (5, 'SIMPLE', 5);
INSERT INTO mark (id, type, user_id) VALUES (6, 'BORING', 4);
INSERT INTO mark (id, type, user_id) VALUES (7, 'USEFUL', 2);
INSERT INTO mark (id, type, user_id) VALUES (8, 'DREAM', 3);

INSERT INTO note_mark (MARK_ID, NOTE_ID) VALUES (1,2);
INSERT INTO note_mark (MARK_ID, NOTE_ID) VALUES (6,8);
INSERT INTO note_mark (MARK_ID, NOTE_ID) VALUES (2,8);
INSERT INTO note_mark (MARK_ID, NOTE_ID) VALUES (8,7);