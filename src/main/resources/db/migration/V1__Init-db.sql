DROP TABLE IF EXISTS message CASCADE;
DROP TABLE IF EXISTS "user" CASCADE;

CREATE TABLE message (message_id UUID NOT NULL, message VARCHAR(255), user_id UUID, PRIMARY KEY (message_id));
CREATE TABLE "user" (user_id UUID NOT NULL, name VARCHAR(255), password VARCHAR(255), PRIMARY KEY (user_id));

ALTER TABLE "user" ADD CONSTRAINT UK__name UNIQUE (name);
ALTER TABLE message ADD CONSTRAINT FK__message_user FOREIGN KEY (user_id) REFERENCES "user";