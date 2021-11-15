package com.practice.com.storedetails.model

import java.util.*
import javax.persistence.*

@Entity
data class AddressPeriod(
    var dateValidFrom: Date,
    var dateValidUntil: Date,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "store_address_id", referencedColumnName = "id")
    var storeAddress: StoreAddress
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    open var id: Long? = null
}