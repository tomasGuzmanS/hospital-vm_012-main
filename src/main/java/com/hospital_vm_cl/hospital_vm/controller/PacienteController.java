package com.hospital_vm_cl.hospital_vm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital_vm_cl.hospital_vm.model.Paciente;
import com.hospital_vm_cl.hospital_vm.service.PacienteService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {

        List<Paciente> pacientes = pacienteService.findAll();

        if (pacientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pacientes);
    }

    @PostMapping
    public ResponseEntity<Paciente> guardar(@RequestBody Paciente paciente) {
        Paciente nuevoPaciente = pacienteService.save(paciente);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPaciente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtenerPorId(@PathVariable int id) {
        try {
            Paciente paciente = pacienteService.findById(id);
            return ResponseEntity.ok(paciente);
        } catch (Exception e) {
            // retorna estatus 404
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizarPaciente(@PathVariable Integer id, @RequestBody Paciente paciente) {
        try {
            Paciente actualPaciente = pacienteService.findById(id);

            actualPaciente.setId(id);
            actualPaciente.setRun(paciente.getRun());
            actualPaciente.setNombres(paciente.getNombres());
            actualPaciente.setApellidos(paciente.getApellidos());
            actualPaciente.setCorreo(paciente.getCorreo());
            actualPaciente.setFechaNacimiento(paciente.getFechaNacimiento());

            pacienteService.save(actualPaciente);
            return ResponseEntity.ok(actualPaciente);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
