from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker, declarative_base

DATABASE_POSTGRESQL = 'postgresql://root:root@localhost:5432/academy'

engine = create_engine(DATABASE_POSTGRESQL)

session = sessionmaker(engine)

Base = declarative_base()

