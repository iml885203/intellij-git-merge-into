package com.github.iml885203.intellijgitmergeinto

class MergeIntoException(val errorCode: EnumErrorCode, override val message: String) : Throwable()
