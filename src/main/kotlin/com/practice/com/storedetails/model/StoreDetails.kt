package com.practice.com.storedetails.model

import java.util.*
import javax.persistence.*

@Entity
class StoreDetails(
    var name: String,
    var status: String,
    var createdAt: Date,
    var updatedAt: Date,
    @OneToMany(cascade = [CascadeType.ALL])
    var addressPeriod: List<AddressPeriod>
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    open var id: Long? = null
}