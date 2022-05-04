--
-- PostgreSQL database dump
--

-- Dumped from database version 14.0 (Debian 14.0-1.pgdg110+1)
-- Dumped by pg_dump version 14.0 (Debian 14.0-1.pgdg110+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: monitor_type; Type: TABLE; Schema: public; Owner: agnieszka
--

CREATE TABLE public.monitor_type (
    id character varying(255) NOT NULL,
    userid character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    created_at timestamp without time zone NOT NULL
);


ALTER TABLE public.monitor_type OWNER TO agnieszka;

--
-- Name: monitor_type monitor_type_pkey; Type: CONSTRAINT; Schema: public; Owner: agnieszka
--

ALTER TABLE ONLY public.monitor_type
    ADD CONSTRAINT monitor_type_pkey PRIMARY KEY (id);


--
-- Name: monitor_type user_id_name; Type: CONSTRAINT; Schema: public; Owner: agnieszka
--

ALTER TABLE ONLY public.monitor_type
    ADD CONSTRAINT user_id_name UNIQUE (userid, name);


--
-- PostgreSQL database dump complete
--

