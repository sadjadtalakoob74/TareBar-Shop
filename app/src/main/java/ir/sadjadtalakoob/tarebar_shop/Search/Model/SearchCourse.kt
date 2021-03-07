package com.example.senthil.kotlin_recyclerview.Model

class SearchCourse{
    var courseName: String? = null
    var courseDescrip: String? = null

    constructor(courseName: String, courseDescrip: String) {
        this.courseName = courseName
        this.courseDescrip = courseDescrip
    }
}