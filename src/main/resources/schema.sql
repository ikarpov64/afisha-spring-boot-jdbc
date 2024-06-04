SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET default_tablespace = '';

SET default_with_oids = false;

SET SEARCH_PATH = 'application';

-- Удаляем дефолтную схему
DROP SCHEMA IF EXISTS public CASCADE;

-- Создаем новую схему
CREATE SCHEMA IF NOT EXISTS application;

--- Удаляем таблицы
DROP TABLE IF EXISTS event_type;
DROP TABLE IF EXISTS place;
DROP TABLE IF EXISTS event;
DROP TABLE IF EXISTS ticket;

--- Создаем таблицу "Тип мероприятия"
CREATE TABLE event_type (
	id serial PRIMARY KEY NOT NULL,
	name varchar(10) NOT NULL
);

--- Создаем таблицу "Место проведения мероприятия"
CREATE TABLE place (
	id serial PRIMARY KEY NOT NULL,
	name varchar(125) NOT NULL,
	address varchar(60) NOT NULL,
	city varchar(15) NOT NULL
);

--- Создаем таблицу "Мероприятие"
CREATE TABLE event (
	id serial PRIMARY KEY NOT NULL,
	name varchar(125) NOT NULL,
	event_type_id integer REFERENCES event_type (id),
	event_date TIMESTAMP without time zone,
	place_id integer REFERENCES place (id)
);

--- Создаем таблицу "Билет"
CREATE TABLE ticket (
	id serial PRIMARY KEY NOT NULL,
	event_id integer REFERENCES event (id),
	client_email varchar(60),
	price numeric(10,2),
	is_sold boolean DEFAULT false
);