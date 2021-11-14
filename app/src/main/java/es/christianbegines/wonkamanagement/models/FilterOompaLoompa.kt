package es.christianbegines.wonkamanagement.models

class FilterOompaLoompa{

    var gender: String? = null
    var profession: String? = null
    var name: String? = null

    constructor()

    constructor(name:String){
        this.name = name
    }

    constructor(nameFilter:String?, genderFilter: String?, professionFilter: String?){
        this.name = nameFilter
        this.profession = professionFilter
        this.gender = genderFilter
    }

}
