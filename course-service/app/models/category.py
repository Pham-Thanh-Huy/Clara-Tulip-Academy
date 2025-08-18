from sqlalchemy import Column, Integer, NVARCHAR, Text, Boolean, DateTime

from app.config.database import Base

'''
    *** 16/08/2025 
    *** Create category model -> map to database (PostgreSql)
'''
class Category(Base):
    __tablename__ = 'category'

    id = Column(Integer, primary_key=True)
    name = Column(NVARCHAR(30), nullable=False, unique=True)
    description = Column(Text, nullable=True)
    user_id = Column(Integer, nullable=False)  # ---> USER_ID IN USER-SERVICE!
    parent_id = Column(Integer)
    is_deleted  = Column(Boolean, default=0, nullable=False)
    created_at = Column(DateTime)
    updated_at = Column(DateTime)
