--
-- PostgreSQL database dump
--

-- Dumped from database version 12.0
-- Dumped by pg_dump version 12.0

-- Started on 2019-12-24 02:01:31

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
-- TOC entry 202 (class 1259 OID 57353)
-- Name: clients; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clients (
    discount double precision NOT NULL,
    telegram integer NOT NULL,
    id integer NOT NULL
);


ALTER TABLE public.clients OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 57360)
-- Name: comments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.comments (
    id integer NOT NULL,
    content character varying(255),
    rating integer NOT NULL,
    client_id integer,
    service_id integer
);


ALTER TABLE public.comments OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 57358)
-- Name: comments_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.comments_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.comments_id_seq OWNER TO postgres;

--
-- TOC entry 2911 (class 0 OID 0)
-- Dependencies: 203
-- Name: comments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.comments_id_seq OWNED BY public.comments.id;


--
-- TOC entry 205 (class 1259 OID 57366)
-- Name: employees; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.employees (
    id integer NOT NULL
);


ALTER TABLE public.employees OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 57371)
-- Name: event_to_service; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.event_to_service (
    event_id integer NOT NULL,
    service_id integer NOT NULL
);


ALTER TABLE public.event_to_service OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 57376)
-- Name: events; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.events (
    id integer NOT NULL,
    cost numeric(19,2),
    date timestamp without time zone,
    description character varying(255),
    name character varying(255),
    client_id integer,
    employee_id integer,
    event_category_id integer
);


ALTER TABLE public.events OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 57387)
-- Name: events_categories; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.events_categories (
    id integer NOT NULL,
    name integer,
    price numeric(19,2)
);


ALTER TABLE public.events_categories OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 57385)
-- Name: events_categories_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.events_categories_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.events_categories_id_seq OWNER TO postgres;

--
-- TOC entry 2912 (class 0 OID 0)
-- Dependencies: 209
-- Name: events_categories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.events_categories_id_seq OWNED BY public.events_categories.id;


--
-- TOC entry 207 (class 1259 OID 57374)
-- Name: events_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.events_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.events_id_seq OWNER TO postgres;

--
-- TOC entry 2913 (class 0 OID 0)
-- Dependencies: 207
-- Name: events_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.events_id_seq OWNED BY public.events.id;


--
-- TOC entry 212 (class 1259 OID 57395)
-- Name: requests; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.requests (
    id integer NOT NULL,
    comment character varying(255),
    status integer,
    client_id integer
);


ALTER TABLE public.requests OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 57393)
-- Name: requests_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.requests_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.requests_id_seq OWNER TO postgres;

--
-- TOC entry 2914 (class 0 OID 0)
-- Dependencies: 211
-- Name: requests_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.requests_id_seq OWNED BY public.requests.id;


--
-- TOC entry 214 (class 1259 OID 57403)
-- Name: services; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.services (
    id integer NOT NULL,
    description character varying(255),
    name character varying(255),
    phone integer NOT NULL,
    price numeric(19,2)
);


ALTER TABLE public.services OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 57401)
-- Name: services_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.services_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.services_id_seq OWNER TO postgres;

--
-- TOC entry 2915 (class 0 OID 0)
-- Dependencies: 213
-- Name: services_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.services_id_seq OWNED BY public.services.id;


--
-- TOC entry 216 (class 1259 OID 57414)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    email character varying(255),
    login character varying(255),
    name character varying(255),
    password character varying(255),
    phone integer NOT NULL,
    role integer,
    surname character varying(255)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 57412)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 2916 (class 0 OID 0)
-- Dependencies: 215
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 2732 (class 2604 OID 57363)
-- Name: comments id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comments ALTER COLUMN id SET DEFAULT nextval('public.comments_id_seq'::regclass);


--
-- TOC entry 2733 (class 2604 OID 57379)
-- Name: events id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.events ALTER COLUMN id SET DEFAULT nextval('public.events_id_seq'::regclass);


--
-- TOC entry 2734 (class 2604 OID 57390)
-- Name: events_categories id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.events_categories ALTER COLUMN id SET DEFAULT nextval('public.events_categories_id_seq'::regclass);


--
-- TOC entry 2735 (class 2604 OID 57398)
-- Name: requests id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.requests ALTER COLUMN id SET DEFAULT nextval('public.requests_id_seq'::regclass);


--
-- TOC entry 2736 (class 2604 OID 57406)
-- Name: services id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.services ALTER COLUMN id SET DEFAULT nextval('public.services_id_seq'::regclass);


--
-- TOC entry 2737 (class 2604 OID 57417)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 2891 (class 0 OID 57353)
-- Dependencies: 202
-- Data for Name: clients; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.clients (discount, telegram, id) FROM stdin;
0	456	4
0	0	27
\.


--
-- TOC entry 2893 (class 0 OID 57360)
-- Dependencies: 204
-- Data for Name: comments; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.comments (id, content, rating, client_id, service_id) FROM stdin;
\.


--
-- TOC entry 2894 (class 0 OID 57366)
-- Dependencies: 205
-- Data for Name: employees; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.employees (id) FROM stdin;
24
26
\.


--
-- TOC entry 2895 (class 0 OID 57371)
-- Dependencies: 206
-- Data for Name: event_to_service; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.event_to_service (event_id, service_id) FROM stdin;
\.


--
-- TOC entry 2897 (class 0 OID 57376)
-- Dependencies: 208
-- Data for Name: events; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.events (id, cost, date, description, name, client_id, employee_id, event_category_id) FROM stdin;
1	0.00	2019-12-23 22:42:17.673	some data	name	27	24	3
2	0.00	2019-12-23 22:42:17.673	new data	event 	27	26	2
\.


--
-- TOC entry 2899 (class 0 OID 57387)
-- Dependencies: 210
-- Data for Name: events_categories; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.events_categories (id, name, price) FROM stdin;
2	1	150.00
3	2	550.00
4	3	400.00
5	4	1000.00
6	5	100.00
7	6	700.00
\.


--
-- TOC entry 2901 (class 0 OID 57395)
-- Dependencies: 212
-- Data for Name: requests; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.requests (id, comment, status, client_id) FROM stdin;
2	i want birthday with photo	0	27
3	i want party	0	27
1	comment	0	27
\.


--
-- TOC entry 2903 (class 0 OID 57403)
-- Dependencies: 214
-- Data for Name: services; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.services (id, description, name, phone, price) FROM stdin;
1	for prom	photo	918	40.00
2	for wedding	video	1302	100.00
3	for prom and party	akute	2176418	300.00
\.


--
-- TOC entry 2905 (class 0 OID 57414)
-- Dependencies: 216
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, email, login, name, password, phone, role, surname) FROM stdin;
4	admin@gmail.com	user	user	$2a$10$kpkNU3Ae1WjfmyjSxH9W7.70/fz9J5I9tTapw.a7UhrBBzDMQM.JG	123	0	com
20	super@gmail.com	admin	admin	$2a$10$thUD0jRlJfW4IWQMGLaiION.mvsmPqv4fDDAep99j/XBCpcLq3jbW	23	2	surname
24	new	employee	employee	$2a$10$sY55Jy3BLmKpe5JGmfR/w.hQz2QJjcE.50gwutJCrAySa33xalzTi	0	1	new
26	cisco	cisco	cisco	$2a$10$6BYYMzGVf.2Bwmf3nfaxbO5kWBaLaavDKLylyL15ejFzLKklySYIu	0	1	cisco
27	client	client	client	$2a$10$1rgcN044yT2pDzwjkaQm5.Y0mgHaHpB1JWnrLaN0s8MNyzFohlhdy	0	0	string
\.


--
-- TOC entry 2917 (class 0 OID 0)
-- Dependencies: 203
-- Name: comments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.comments_id_seq', 1, false);


--
-- TOC entry 2918 (class 0 OID 0)
-- Dependencies: 209
-- Name: events_categories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.events_categories_id_seq', 8, true);


--
-- TOC entry 2919 (class 0 OID 0)
-- Dependencies: 207
-- Name: events_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.events_id_seq', 2, true);


--
-- TOC entry 2920 (class 0 OID 0)
-- Dependencies: 211
-- Name: requests_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.requests_id_seq', 3, true);


--
-- TOC entry 2921 (class 0 OID 0)
-- Dependencies: 213
-- Name: services_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.services_id_seq', 3, true);


--
-- TOC entry 2922 (class 0 OID 0)
-- Dependencies: 215
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 27, true);


--
-- TOC entry 2738 (class 2606 OID 65547)
-- Name: users check_phone; Type: CHECK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE public.users
    ADD CONSTRAINT check_phone CHECK ((phone >= 0)) NOT VALID;


--
-- TOC entry 2740 (class 2606 OID 57357)
-- Name: clients clients_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id);


--
-- TOC entry 2742 (class 2606 OID 57365)
-- Name: comments comments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT comments_pkey PRIMARY KEY (id);


--
-- TOC entry 2744 (class 2606 OID 57370)
-- Name: employees employees_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employees
    ADD CONSTRAINT employees_pkey PRIMARY KEY (id);


--
-- TOC entry 2748 (class 2606 OID 57392)
-- Name: events_categories events_categories_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.events_categories
    ADD CONSTRAINT events_categories_pkey PRIMARY KEY (id);


--
-- TOC entry 2746 (class 2606 OID 57384)
-- Name: events events_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT events_pkey PRIMARY KEY (id);


--
-- TOC entry 2750 (class 2606 OID 57400)
-- Name: requests requests_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.requests
    ADD CONSTRAINT requests_pkey PRIMARY KEY (id);


--
-- TOC entry 2752 (class 2606 OID 57411)
-- Name: services services_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.services
    ADD CONSTRAINT services_pkey PRIMARY KEY (id);


--
-- TOC entry 2754 (class 2606 OID 57422)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 2755 (class 2606 OID 57423)
-- Name: clients fk1hgwdp9vl25xl9i7s354sifey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clients
    ADD CONSTRAINT fk1hgwdp9vl25xl9i7s354sifey FOREIGN KEY (id) REFERENCES public.users(id);


--
-- TOC entry 2763 (class 2606 OID 57463)
-- Name: events fk2670pt1ebwjukmrpvvpm7giul; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT fk2670pt1ebwjukmrpvvpm7giul FOREIGN KEY (event_category_id) REFERENCES public.events_categories(id);


--
-- TOC entry 2759 (class 2606 OID 57443)
-- Name: event_to_service fk4y7m6f4sq05fmb8kmpcjucv74; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.event_to_service
    ADD CONSTRAINT fk4y7m6f4sq05fmb8kmpcjucv74 FOREIGN KEY (service_id) REFERENCES public.services(id);


--
-- TOC entry 2760 (class 2606 OID 57448)
-- Name: event_to_service fk6fnykkifufv75kaxbknbxefsu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.event_to_service
    ADD CONSTRAINT fk6fnykkifufv75kaxbknbxefsu FOREIGN KEY (event_id) REFERENCES public.events(id);


--
-- TOC entry 2758 (class 2606 OID 57438)
-- Name: employees fkd6th9xowehhf1kmmq1dsseq28; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employees
    ADD CONSTRAINT fkd6th9xowehhf1kmmq1dsseq28 FOREIGN KEY (id) REFERENCES public.users(id);


--
-- TOC entry 2756 (class 2606 OID 57428)
-- Name: comments fkeuitc73vwtp21jvi6slqwcxfq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT fkeuitc73vwtp21jvi6slqwcxfq FOREIGN KEY (client_id) REFERENCES public.clients(id);


--
-- TOC entry 2761 (class 2606 OID 57453)
-- Name: events fkh535ibmxvgfxkqkmoo8uyn193; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT fkh535ibmxvgfxkqkmoo8uyn193 FOREIGN KEY (client_id) REFERENCES public.clients(id);


--
-- TOC entry 2757 (class 2606 OID 57433)
-- Name: comments fkk9dsamacgncqfts9s0nax4wc0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT fkk9dsamacgncqfts9s0nax4wc0 FOREIGN KEY (service_id) REFERENCES public.services(id);


--
-- TOC entry 2764 (class 2606 OID 57468)
-- Name: requests fkljmj9fpy346ea5hejj9ej6x6o; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.requests
    ADD CONSTRAINT fkljmj9fpy346ea5hejj9ej6x6o FOREIGN KEY (client_id) REFERENCES public.clients(id);


--
-- TOC entry 2762 (class 2606 OID 57458)
-- Name: events fkpsc4lgvb62aba82t5otqdcd1g; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT fkpsc4lgvb62aba82t5otqdcd1g FOREIGN KEY (employee_id) REFERENCES public.employees(id);


-- Completed on 2019-12-24 02:01:32

--
-- PostgreSQL database dump complete
--

