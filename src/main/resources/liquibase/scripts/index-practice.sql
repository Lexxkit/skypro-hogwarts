-- liquibase formatted sql

-- changeset oalekseenko:1
create index student_name_index on student (name);

-- changeset oalekseenko:2
create index faculty_nc_index on faculty (name, color);
