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
-- Name: field_types; Type: TABLE; Schema: public; Owner: agnieszka
--

CREATE TABLE public.field_types (
    id integer NOT NULL,
    type integer NOT NULL
);


ALTER TABLE public.field_types OWNER TO agnieszka;

--
-- Name: field_types_id_seq; Type: SEQUENCE; Schema: public; Owner: agnieszka
--

ALTER TABLE public.field_types ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.field_types_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: field_types field_types_pkey; Type: CONSTRAINT; Schema: public; Owner: agnieszka
--

ALTER TABLE ONLY public.field_types
    ADD CONSTRAINT field_types_pkey PRIMARY KEY (id);


--
-- Name: field_types field_types_type_key; Type: CONSTRAINT; Schema: public; Owner: agnieszka
--

ALTER TABLE ONLY public.field_types
    ADD CONSTRAINT field_types_type_key UNIQUE (type);


--
-- PostgreSQL database dump complete
--

