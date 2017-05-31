package com.navarna.computerdb.controller.rest;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.navarna.computerdb.dto.ComputerDTO;
import com.navarna.computerdb.exception.RestException;
import com.navarna.computerdb.mapper.TransformationToDTO;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;
import com.navarna.computerdb.service.ServiceComputer;

@RestController()
@RequestMapping("/api/computer")
public class ApiComputerSpring {
    @Autowired
    private ServiceComputer servComputer;

    @GetMapping("/{numeroPage}/{element}")
    public Page<ComputerDTO> getPageComputer(@PathVariable int numeroPage, @PathVariable int element) {
        if ((numeroPage >= 0) && (element > 0)) {
            Page<ComputerDTO> page = TransformationToDTO
                    .pageComputerToPageDTO(servComputer.liste(numeroPage, element, "id", "ASC"));
            if (!page.estVide()) {
                return page;
            }
            throw RestException.notFound();
        }
        throw RestException.illegalArgument();
    }

    @GetMapping("/{id}")
    public ComputerDTO getComputer(@PathVariable int id) {
        if (id > 0) {
            Optional<Computer> computer = servComputer.findById(id);
            if (computer.isPresent()) {
                Optional<ComputerDTO> computerdto = TransformationToDTO.computerToDTO(computer.get());
                if (computerdto.isPresent()) {
                    return computerdto.get();
                }
            }
            throw RestException.notFound();
        }
        throw RestException.illegalArgument();
    }

    @PutMapping
    public ComputerDTO insertComputer(@Valid @RequestBody ComputerDTO computerdto, BindingResult result) {
        if (!result.hasErrors()) {
            Optional<Computer> computer = TransformationToDTO.dtoToComputer(computerdto);
            if (computer.isPresent()) {
                if (servComputer.insert(computer.get())) {
                    return computerdto;
                }
            }
        }
        throw RestException.illegalArgument();
    }

    @PostMapping("/{id}")
    public ComputerDTO updateComputer(@PathVariable int id, @Valid @RequestBody ComputerDTO computerdto,
            BindingResult result) {
        if ((!result.hasErrors()) && (id > 0) && (id == computerdto.getId())) {
            Optional<Computer> existe = servComputer.findById(id);
            if (existe.isPresent()) {
                Optional<Computer> computer = TransformationToDTO.dtoToComputer(computerdto);
                if (computer.isPresent()) {
                    if (servComputer.update(computer.get())) {
                        return computerdto;
                    }
                }
            }
            else {
                throw RestException.inexistent();
            }
        }
        throw RestException.illegalArgument();
    }

    @DeleteMapping("/{id}")
    public void deleteComputer (@PathVariable int id) {
        if(id > 0) {
            boolean reussir = servComputer.delete(id);
            if(!reussir) {
                throw RestException.inexistent();
            }
        }
        else {
            throw RestException.illegalArgument();
        }
    }
}
