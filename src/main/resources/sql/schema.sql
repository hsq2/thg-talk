\connect demotask;

CREATE TABLE Users(
userID UUID PRIMARY KEY,
userName VARCHAR(50) NOT NULL,
dateCreated TIMESTAMP NOT NULL
);

INSERT INTO  Users (userID, userName, dateCreated) values ('61614667-d279-11e7-a5ac-f941ac8dfc39', 'user1', '2017-03-31 9:30:20');

CREATE TABLE SubTalks(
subTalkID UUID PRIMARY KEY,
creatorID UUID NOT NULL,
subTalkTitle VARCHAR(50) NOT NULL,
subTalkDescription TEXT NOT NULL,
subTalkImageURL TEXT NOT NULL,
dateCreated TIMESTAMP NOT NULL,
FOREIGN KEY(creatorID) REFERENCES Users(userID) ON DELETE CASCADE
);

INSERT INTO SubTalks (subTalkID, creatorID, subTalkTitle, subTalkDescription, subTalkImageURL, dateCreated) values ('61614667-d279-11e7-a5ac-f941ac8dfc40', '61614667-d279-11e7-a5ac-f941ac8dfc39', 'THG SubTalk', 'A SubTalk', 'thg.com', '2017-03-31 9:30:20');

CREATE TABLE Posts(
postID UUID PRIMARY KEY,
userID UUID NOT NULL,
subTalkID UUID NOT NULL,
postTitle VARCHAR(50) NOT NULL,
postContent TEXT NOT NULL,
dateCreated TIMESTAMP NOT NULL,
FOREIGN KEY(userID) REFERENCES Users(userID) ON DELETE CASCADE,
FOREIGN KEY(subTalkID) REFERENCES SubTalks(subTalkID) ON DELETE CASCADE
);

INSERT INTO Posts (postID, userID, subTalkID, postTitle, postContent, dateCreated) values ('61614667-d279-11e7-a5ac-f941ac8dfc41', '61614667-d279-11e7-a5ac-f941ac8dfc39', '61614667-d279-11e7-a5ac-f941ac8dfc40', 'First post', 'Nice', '2017-03-31 9:30:20');

CREATE TABLE Comments(
commentID UUID PRIMARY KEY,
userID UUID NOT NULL,
postID UUID NOT NULL,
commentContent TEXT NOT NULL,
dateCreated TIMESTAMP NOT NULL,
FOREIGN KEY(userID) REFERENCES Users(userID) ON DELETE CASCADE,
FOREIGN KEY(postID) REFERENCES Posts(postID) ON DELETE CASCADE
);

INSERT INTO Comments (commentID, userID, postID, commentContent, dateCreated) values ('61614667-d279-11e7-a5ac-f941ac8dfc42', '61614667-d279-11e7-a5ac-f941ac8dfc39', '61614667-d279-11e7-a5ac-f941ac8dfc41', 'Nice', '2017-03-31 9:30:20');

CREATE TYPE VOTE AS ENUM ('UPVOTE', 'DOWNVOTE');

CREATE TABLE Votes(
voteID UUID PRIMARY KEY,
userID UUID NOT NULL,
vote VOTE NOT NULL
);

INSERT INTO Votes (voteID, userID, vote) values ('61614667-d279-11e7-a5ac-f941ac8dfc43', '61614667-d279-11e7-a5ac-f941ac8dfc39', 'UPVOTE');
INSERT INTO Votes (voteID, userID, vote) values ('61614667-d279-11e7-a5ac-f941ac8dfc44', '61614667-d279-11e7-a5ac-f941ac8dfc39', 'UPVOTE');

CREATE TABLE VotesPosts(
voteID UUID NOT NULL,
postID UUID NOT NULL,
FOREIGN KEY(voteID) REFERENCES Votes(voteID) ON DELETE CASCADE,
FOREIGN KEY(postID) REFERENCES Posts(postID) ON DELETE CASCADE
);

INSERT INTO VotesPosts (voteID, postID) values ('61614667-d279-11e7-a5ac-f941ac8dfc43', '61614667-d279-11e7-a5ac-f941ac8dfc41');

CREATE TABLE VotesComments(
voteID UUID NOT NULL,
commentID UUID NOT NULL,
FOREIGN KEY(voteID) REFERENCES Votes(voteID) ON DELETE CASCADE,
FOREIGN KEY(commentID) REFERENCES Comments(commentID) ON DELETE CASCADE
);

INSERT INTO VotesComments (voteID, commentID) values ('61614667-d279-11e7-a5ac-f941ac8dfc44', '61614667-d279-11e7-a5ac-f941ac8dfc42');