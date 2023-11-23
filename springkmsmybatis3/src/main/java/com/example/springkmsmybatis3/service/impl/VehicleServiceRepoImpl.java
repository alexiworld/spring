package com.example.springkmsmybatis3.service.impl;

import com.example.springkmsmybatis3.model.EncrappedString;
import com.example.springkmsmybatis3.model.EncryptedString;
import com.example.springkmsmybatis3.dao.entity.VehicleEntity;
import com.example.springkmsmybatis3.dao.repository.VehicleEntityXMLRepository;
import com.example.springkmsmybatis3.model.Vehicle;
import com.example.springkmsmybatis3.model.VehicleResponse;
import com.example.springkmsmybatis3.service.VehicleService;
import com.example.springkmsmybatis3.utils.ModelUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service("vehicleServiceRepoImpl")
@Log4j2
public class VehicleServiceRepoImpl implements VehicleService {

    @Autowired
    VehicleEntityXMLRepository vehicleEntityXMLRepository;

    public VehicleResponse process(Vehicle vehicle){
        VehicleEntity vehicleEntity = ModelUtils.map(vehicle);

        //// Using sequence generator is a bit tricky but managed to do it leveraging
        //// setval for the sequence value, is_called to get the right id to be the
        //// next one, etc. The code needs to use "saveWithId" insert defined in the
        //// mapper.
        //Long nextId = vehicleEntityXMLRepository.findNextId();
        //vehicleEntity.setId(nextId);

        // The other and better alternative for managing the entity id would be not to
        // set the id and use some of mybatis+postgres features to return the id from
        // select and set it in the parameter entity. See the xml file for more details.
        Optional<VehicleEntity> saveVehicleEntity = vehicleEntityXMLRepository.save(vehicleEntity);

        VehicleResponse response = new VehicleResponse();
        if (saveVehicleEntity.isPresent()) {
            response.setId(saveVehicleEntity.get().getId());
        }
        return response;
    }

    public Vehicle fetchVehicle(Long id) {
        Optional<Vehicle> result = vehicleEntityXMLRepository.findById(id).map(ModelUtils::map);
        if(result.isPresent()){
            return result.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No vehicle found with id "+id);
    }

    public Vehicle fetchVehicleByVin(String vinNumber) {
        Optional<Vehicle> result = vehicleEntityXMLRepository.findByVinNumber(EncrappedString.to(vinNumber)).map(ModelUtils::map);
        if(result.isPresent()){
            return result.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No vehicle found with vin "+vinNumber);
    }

    public List<Vehicle> fetchVehicles() {
        Iterable<VehicleEntity> result = vehicleEntityXMLRepository.findAll();
        return StreamSupport.stream(result.spliterator(), false).map(ModelUtils::map).toList();
    }

    public List<Vehicle> fetchVehiclesByYear(String year) {
        List<VehicleEntity> result = vehicleEntityXMLRepository.findAllByYear(year);
        return result.stream().map(ModelUtils::map).toList();
    }

    public List<Vehicle> fetchVehiclesByAgent(String agent) {
        List<VehicleEntity> result = vehicleEntityXMLRepository.findAllByCreatedBy(EncryptedString.to(agent));
        return result.stream().map(ModelUtils::map).toList();
    }

    public void deleteVehicle(Long id) {
        vehicleEntityXMLRepository.deleteById(id);
    }

}