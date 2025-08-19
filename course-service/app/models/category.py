from sqlalchemy import Column, BigInteger, VARCHAR, Text, Boolean, DateTime, Double
from sqlalchemy.orm import relationship

from app.config.database import Base

'''
    *** 16/08/2025 
    *** Create category model -> map to database (PostgreSql)
'''
class Category(Base):
    __tablename__ = 'category'

    id = Column(BigInteger, primary_key=True)
    name = Column(VARCHAR(30), nullable=False, unique=True)
    description = Column(Text, nullable=True)
    parent_id = Column(Double, nullable=True)
    is_deleted  = Column(Boolean, default=0, nullable=False)
    created_at = Column(DateTime)
    updated_at = Column(DateTime, nullable=True)

    #RELATIONSHIP
    course = relationship("Course" ,secondary="category_course", back_populates="category")

