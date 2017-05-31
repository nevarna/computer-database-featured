package com.navarna.computerdb.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.navarna.computerdb.dto.CompanyDTO;
import com.navarna.computerdb.exception.RestException;
import com.navarna.computerdb.mapper.TransformationToDTO;
import com.navarna.computerdb.model.Page;
import com.navarna.computerdb.service.ServiceCompany;

@RestController()
@RequestMapping("/api/company")
public class ApiCompanySpring {
    @Autowired
    private ServiceCompany servCompany;

    @GetMapping("/{numeroPage}/{element}")
    public Page<CompanyDTO> getPageCompany(@PathVariable int numeroPage, @PathVariable int element) {
        System.out.println("in");
        if ((numeroPage >= 0) && (element > 0)) {
            Page<CompanyDTO> page = TransformationToDTO.pageCompanyToPageDTO(servCompany.liste(numeroPage, element));
            if (!page.estVide()) {
                return page;
            }
            throw RestException.notFound();
        }
        throw RestException.illegalArgument();
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable int id) {
        if (id > 0) {
            boolean reussir = servCompany.delete(id);
            if (!reussir) {
                throw RestException.inexistent();
            }
        } else {
            throw RestException.illegalArgument();
        }
    }

}
