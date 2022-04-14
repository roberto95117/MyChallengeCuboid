drop table if exists "CUBOIDS" CASCADE;
drop table if exists "BAGS" CASCADE;
create sequence IF NOT EXISTS hibernate_sequence start with 4 increment by 1;

CREATE TABLE "BAGS" (
    "ID" bigint not null, 
    "VOLUME" double not null,
     primary key ("ID")
);

create table  "CUBOIDS" (
    "ID" bigint not null, 
    "DEPTH" float not null, 
    "HEIGHT" float not null, 
    "WIDTH" float not null, 
    "BAG_ID" bigint not null,
     primary key ("ID")
);

alter table "CUBOIDS" add constraint "FKgwdq976r5nwtgf7smd9beq1ix" foreign key ("BAG_ID") references "BAGS";

insert into "BAGS" ("ID", "VOLUME") values (1, 20.0);
insert into "BAGS" ("ID", "VOLUME") values (2, 30.0);
insert into "BAGS" ("ID", "VOLUME") values (3, 100.0);

insert into "CUBOIDS" ("ID", "DEPTH", "HEIGHT", "WIDTH", "BAG_ID") values (1, 2.0, 3.0, 5.0, 1);
insert into "CUBOIDS" ("ID", "DEPTH", "HEIGHT", "WIDTH", "BAG_ID") values (2, 2.0, 4.0, 4.0, 2);
insert into "CUBOIDS" ("ID", "DEPTH", "HEIGHT", "WIDTH", "BAG_ID") values (3, 3.0, 3.0, 3.0, 3);