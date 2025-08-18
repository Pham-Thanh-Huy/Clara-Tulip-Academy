from sqlalchemy import Column, NVARCHAR, Text, Integer, ForeignKey, DateTime, Boolean
from sqlalchemy.orm import relationship

from app.config.database import Base

'''
    *** 16/08/2025 
    *** Create Course model -> map to database (PostgreSql)
'''
class Course(Base):
    __tablename__ = 'course'

    id = Column(Integer, primary_key=True)
    name = Column(NVARCHAR(255), nullable=False, unique=True)
    introduction = Column(NVARCHAR(255), nullable=False, unique=True)
    description = Column(Text, nullable=True)
    user_id = Column(Integer, nullable=False)  # ---> USER_ID IN USER-SERVICE!
    created_at = Column(DateTime)
    updated_at = Column(DateTime)
    is_deleted  = Column(Boolean, default=0, nullable=False)

    # RELATIONSHIP
    category_id = Column(Integer, ForeignKey('category.id'))
    category = relationship("Category", backref='course')

