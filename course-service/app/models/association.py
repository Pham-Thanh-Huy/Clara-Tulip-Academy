from sqlalchemy import Table, Column, ForeignKey, BigInteger

from app.config.database import Base

'''
    *** 17/08/2025 
    *** 
'''

category_course = Table(
    'category_course', Base.metadata,
    Column('category_id', BigInteger,ForeignKey("category.id"), primary_key=True),
    Column('course_id', BigInteger, ForeignKey("course.id"), primary_key=True),
)
