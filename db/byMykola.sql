create table UserBank
(
  UserBankId          bigint not null auto_increment,
  UserId              bigint not null,
  BankValue           double,
  primary key (UserBankId)
);

create table User
(
  UserId               bigint not null auto_increment,
  Username             varchar(256),
  Email                varchar(256),
  Password             varchar(256),
  primary key (UserId)
);

create table Bet
(
  BetId               bigint not null auto_increment,
  UserId              bigint,
  EventResultId       bigint,
  BetStatus           varchar(256),
  Coefficient         double,
  BetValue            double,
  primary key(BetId)
);

create table Participant
(
  ParticipantId     bigint not null auto_increment,
  EventId           varchar(64),
  primary key(ParticipantId)
);

create table BetType
(
  BetTypeId           bigint not null auto_increment,
  Description         varchar(256),
  primary key(BetTypeId)
);

create table EventResult
(
  EventResultId       bigint not null auto_increment,
  EventId             varchar(64),
  BetTypeId           bigint,
  Coefficient         double,
  Result              boolean,
  primary key(EventResultId)
);

create table EventType
(
  EventTypeId         bigint not null auto_increment,
  Description         varchar(256),
  ParticipantTypeId   bigint,
  primary key (EventTypeId)
);

create table Event
(
  EventId               varchar(64) not null,
  EventTypeId           bigint,
  Description           varchar(256),
  Tournament            varchar(256),
  StartTime             datetime,
  EventStatus           varchar(256),
  primary key (EventId)
);

create table FootballTeam
(
  ParticipantId       bigint not null,
  Name                varchar(256),
  Goals               int,
  primary key (ParticipantId)
);

create table ParticipantType
(
  ParticipantTypeId   bigint not null auto_increment,
  Description         varchar(256),
  primary key (ParticipantTypeId)
);



alter table UserBank add constraint FK_UserUserBank foreign key (UserId)
references User(UserId) on delete restrict on update restrict;

alter table Bet add constraint FK_BetEventResult foreign key (EventResultId)
references EventResult(EventResultId) on delete restrict on update restrict;

alter table Bet add constraint FK_BetUser foreign key (UserId)
references User(UserId) on delete restrict on update restrict;

alter table Event add constraint FK_EventEventType foreign key (EventTypeId)
references EventType(EventTypeId) on delete restrict on update restrict;

alter table Participant add constraint FK_EventParticipant foreign key(EventId)
references Event(EventId) on delete restrict on update restrict;

alter table EventResult add constraint FK_EventResultEvent foreign key (EventId)
references Event(EventId) on delete restrict on update restrict;

alter table EventResult add constraint FK_EventResultBetType foreign key (BetTypeId)
references BetType(BetTypeId) on delete restrict on update restrict;

alter table EventType add constraint FK_EventTypeParticipantType foreign key(ParticipantTypeId)
references ParticipantType(ParticipantTypeId) on delete restrict on update restrict;

alter table FootballTeam add constraint FK_ParticipantFootballTeam foreign key(ParticipantId)
references Participant(ParticipantId) on delete restrict on update restrict;

insert into `BetType`(`Description`) value ('1');
insert into `BetType`(`Description`) value ('X');
insert into `BetType`(`Description`) value ('2');

insert into `ParticipantType`(`Description`) value ('Football Team');

insert into `EventType`(`Description`, `ParticipantTypeId`) value ('Football Match', 1);