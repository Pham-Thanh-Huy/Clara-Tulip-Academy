import enum

from sqlalchemy import Column, Integer, Enum, Text, Boolean, BigInteger, ForeignKey
from sqlalchemy.orm import relationship

from app.config.database import Base

'''
    *** 17/08/2025 
    *** Create Lesson Material   model -> map to database (PostgreSql)
'''

class DocumentEnum(enum.Enum):
    PDF = "type/pdf"
    WORD = "type/docx"
    VIDEO = "type/video"
    LINK = "type/link" # INPUT IS LINK VIDEO (example: Link youtube, Link Google Drive, etc...)


class LessonDocument(Base):
    __tablename__ = 'lesson_document'

    id = Column(Integer, primary_key=True)
    document_type = Column(Enum(DocumentEnum), nullable=False)
    document_link = Column(Text, unique=True,  nullable=True)
    is_deleted = Column(Boolean, default=0, nullable=False)


    #RELATIONS SIP
    lesson_id = Column(BigInteger, ForeignKey("lesson.id"), nullable=None)
    lesson = relationship("Lesson", backref="lesson_document")




