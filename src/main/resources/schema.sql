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
DROP TABLE IF EXISTS event_type;


-- Name: event_type; Type: TABLE; Schema: application; Owner: -; Tablespace: 

CREATE TABLE event_type (
	id serial NOT NULL,
	name varchar(10) NOT NULL
);

-- Name: place; Type: TABLE; Schema: application; Owner: -; Tablespace: 

CREATE TABLE place (
	id serial NOT NULL,
	name varchar(125) NOT NULL,
	address varchar(60) NOT NULL,
	city varchar(15) NOT NULL
);

-- Name: event; Type: TABLE; Schema: application; Owner: -; Tablespace: 

CREATE TABLE event (
	id serial NOT NULL,
	name varchar(125) NOT NULL,
	event_type_id integer,
	event_date TIMESTAMP without time zone,
	place_id integer
);

-- Name: ticket; Type: TABLE; Schema: application; Owner: -; Tablespace: 

CREATE TABLE ticket (
	id serial NOT NULL,
	event_id integer,
	client_email varchar(60),
	price numeric(10,2),
	is_sold boolean DEFAULT false
);

---- Data for Name: event_type; Type: TABLE DATA; Schema: application; Owner: -
--INSERT INTO event_type VALUES (1, 'museum');
--INSERT INTO event_type VALUES (2, 'cinema');
--INSERT INTO event_type VALUES (3, 'theater');


-- Name: pk_event_type; Type: CONSTRAINT; Schema: application; Owner: -; Tablespace: 

ALTER TABLE ONLY event_type
    ADD CONSTRAINT pk_event_type PRIMARY KEY (id);

-- Name: pk_place; Type: CONSTRAINT; Schema: application; Owner: -; Tablespace: 

ALTER TABLE ONLY place
    ADD CONSTRAINT pk_place PRIMARY KEY (id);

-- Name: pk_event; Type: CONSTRAINT; Schema: application; Owner: -; Tablespace: 

ALTER TABLE ONLY event
    ADD CONSTRAINT pk_event PRIMARY KEY (id);

-- Name: pk_ticket; Type: CONSTRAINT; Schema: application; Owner: -; Tablespace: 

ALTER TABLE ONLY ticket
    ADD CONSTRAINT pk_ticket PRIMARY KEY (id);


-- Name: fk_event; Type: Constraint; Schema: -; Owner: -

ALTER TABLE ONLY event
    ADD CONSTRAINT fk_event_event_type FOREIGN KEY (event_type_id) REFERENCES event_type (id),
	ADD CONSTRAINT fk_event_place FOREIGN KEY (place_id) REFERENCES place (id);

-- Name: fk_ticket; Type: Constraint; Schema: -; Owner: -

ALTER TABLE ONLY ticket
    ADD CONSTRAINT fk_ticket_event FOREIGN KEY (event_id) REFERENCES event (id);
