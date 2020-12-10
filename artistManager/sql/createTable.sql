create table if not exists artist(id SERIAL PRIMARY KEY,
                                    id_deezer VARCHAR,
                                    id_spotify VARCHAR,
                                    name VARCHAR UNIQUE);

create table if not exists artist_tracking(id SERIAL PRIMARY KEY,
                                            id_artist VARCHAR,
                                            nb_fan INTEGER,
                                            popularity INTEGER,
                                            source VARCHAR,
                                             date_maj TIMESTAMP);

create table if not exists music_tracking(id SERIAL PRIMARY KEY,
                                            id_source VARCHAR,
                                            id_artist VARCHAR,
                                            country_code VARCHAR,
                                            popularity INTEGER,
                                            feat BOOLEAN,
                                            duration INTEGER,
                                            source VARCHAR,
                                            date_maj TIMESTAMP);