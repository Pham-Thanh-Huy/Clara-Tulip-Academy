from sqlalchemy import Column, VARCHAR, Text, BigInteger, DateTime, Boolean
from sqlalchemy.orm import relationship

from app.config.database import Base

'''
    *** 16/08/2025 
    *** Create Course model -> map to database (PostgreSql)
'''
class Course(Base):
    __tablename__ = 'course'

    id = Column(BigInteger, primary_key=True)
    name = Column(VARCHAR(255), nullable=False, unique=True)
    introduction = Column(VARCHAR(255), nullable=False, unique=True)
    description = Column(Text, nullable=True)
    created_at = Column(DateTime)
    updated_at = Column(DateTime, nullable=True)
    is_deleted  = Column(Boolean, default=0, nullable=False)

    # RELATIONSHIP
    category = relationship("Category", secondary="category_course", back_populates="course")

