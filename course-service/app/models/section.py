from sqlalchemy import Column, Integer, VARCHAR, DateTime, ForeignKey, BigInteger
from sqlalchemy.orm import relationship

from app.config.database import Base

'''
    *** 17/08/2025 
    *** Create section model -> map to database (PostgreSql)
'''
class Section(Base):
    __tablename__ = 'section'

    id = Column(BigInteger, primary_key=True)
    name = Column(VARCHAR(100), nullable=False)
    parent_id = Column(BigInteger, nullable=True)
    created_at = Column(DateTime)
    updated_at = Column(DateTime, nullable=True)

    #RELATIONSHIP
    course_id = Column(BigInteger, ForeignKey("course.id"), nullable=False)
    course = relationship("Course", backref="section")

