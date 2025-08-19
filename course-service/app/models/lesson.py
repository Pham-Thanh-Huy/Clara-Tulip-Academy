from sqlalchemy import Column, BigInteger, VARCHAR, DateTime, ForeignKey
from sqlalchemy.orm import relationship

from app.config.database import Base

'''
    *** 17/08/2025 
    *** Create lesson model -> map to database (PostgreSql)
'''
class Lesson(Base):
    __tablename__ = 'lesson'

    id = Column(BigInteger, primary_key=True)
    name = Column(VARCHAR(100), nullable=False)
    created_at = Column(DateTime)
    updated_at = Column(DateTime, nullable=True)

    #RELATIONSHIP
    section_id = Column(BigInteger, ForeignKey("section.id"), nullable=False)
    section = relationship("Section", backref="lesson")

