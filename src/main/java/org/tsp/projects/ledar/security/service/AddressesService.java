package org.tsp.projects.ledar.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tsp.projects.ledar.security.model.Addresses;
import org.tsp.projects.ledar.security.model.LoginInformation;
import org.tsp.projects.ledar.security.payload.request.AddressesPayload;
import org.tsp.projects.ledar.security.repository.AddressesRepository;

/**
 * This class serves as a service class for Addresses entity crud
 * functionalities
 */
@Slf4j
@Service
public class AddressesService {

    private final AddressesRepository addressesRepository;

    @Autowired
    public AddressesService(AddressesRepository addressesRepository) {
        this.addressesRepository = addressesRepository;
    }

    /**
     * This method is used to save new address information for a new user in the
     * system
     */
    public Addresses saveNewAddresses(AddressesPayload addressPayload, LoginInformation creator) {

        if (addressPayload != null) {
            Addresses address = new Addresses(null, addressPayload.getStreetName(), addressPayload.getHouseNo(), addressPayload.getCity(),
                    addressPayload.getLocalGovtArea(), creator, addressPayload.getAddressState(), addressPayload.getAddressCountry(), null, null);

            addressesRepository.save(address);
            return address;
        }
        return null;
    }
}
