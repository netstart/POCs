INSERT INTO User(id, email, name) VALUES (1, 'clayton@gmail.com', 'Clayton');
INSERT INTO User(id, email, name) VALUES (2, 'dany@gmail.com', 'Dany');
INSERT INTO User(id, email, name) VALUES (3, 'cat@gmail.com', 'Catherine');
INSERT INTO User(id, email, name) VALUES (4, 'sof@gmail.com', 'Sophia');


INSERT INTO Post(id, subject, user_id) VALUES (1, 'Como funciona?', 1);
INSERT INTO Comment(id, reply, post_id, user_id) VALUES (1,'Uai funciona assim ó....', 1, 2);
INSERT INTO Comment(id, reply, post_id, user_id) VALUES (2,'Funciona sim, né?', 1, 2);

INSERT INTO Post(id, subject, user_id) VALUES (2, 'Porque não ter uma classe email?', 3);
INSERT INTO Comment(id, reply, post_id, user_id) VALUES (3,'Deveria, dado que trabalhamos com O.O.', 2, 4);
INSERT INTO Comment(id, reply, post_id, user_id) VALUES (4,'Além disso, torna claro que a regra de validação fica no objeto e-mail', 2, 4);