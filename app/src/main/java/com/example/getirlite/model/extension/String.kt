package com.example.getirlite.model.extension

inline val String.title: String get() = substring(0, 1).uppercase() + substring(1)