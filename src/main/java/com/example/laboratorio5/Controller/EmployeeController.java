package com.example.laboratorio5.Controller;


import com.example.laboratorio5.Entity.Department;
import com.example.laboratorio5.Entity.Employee;
import com.example.laboratorio5.Entity.Job;
import com.example.laboratorio5.Repository.DepartmentRepository;
import com.example.laboratorio5.Repository.EmployeesRepository;
import com.example.laboratorio5.Repository.JobRepository;
import com.example.laboratorio5.dto.EmployeeDto;
import com.example.laboratorio5.dto.ManagerDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value="/empleado")
public class EmployeeController {

    EmployeesRepository employeesRepository;
    DepartmentRepository departmentRepository;
    JobRepository jobRepository;

    public EmployeeController(EmployeesRepository employeesRepository, DepartmentRepository departmentRepository, JobRepository jobRepository) {
        this.employeesRepository = employeesRepository;
        this.departmentRepository = departmentRepository;
        this.jobRepository = jobRepository;
    }

    @GetMapping("listar")
    public String listaEmployee(Model model ){

        List<EmployeeDto> listaempleados = employeesRepository.listarEmpleados();
        model.addAttribute("listaempleados",listaempleados);


        return "employee/listar";
    }
    @GetMapping("nuevo")
    public String nuevoEmployeeForm( Model model) {

        List<Job> listaTrabajos = jobRepository.findAll();
        List<Department> listaDepartamentos = departmentRepository.findAll();
        List<ManagerDto> listaManagers = employeesRepository.buscarManager();
        model.addAttribute("listaTrabajos",listaTrabajos);
        model.addAttribute("listaDepartamentos",listaDepartamentos);
        model.addAttribute("listaManagers",listaManagers);

        return "employee/newFrm";
    }

    @PostMapping("guardar")
    public String guardarEmployee(@RequestParam("id") Integer id,
                                  @RequestParam("firstName") String firstName,
                                  @RequestParam("lastName") String lastName,
                                  @RequestParam("email") String email,
                                  @RequestParam("jobid") String jobid,
                                  @RequestParam("salary") Double salary,
                                  @RequestParam("managerid") Integer managerid,
                                  @RequestParam("departmentid") Integer departmentid,
                                  RedirectAttributes attr) {

        employeesRepository.guardaremployee(firstName,lastName,email,jobid,salary,managerid,departmentid,id);
        attr.addFlashAttribute("msg","empleado actualizado");
        return "redirect:/empleado/listar";
    }

    @GetMapping("editar")
    public String editarEmployee(@RequestParam ("id") Integer EmpId, Model model) {

        Optional<Employee> employeeOptional = employeesRepository.findById(EmpId);
        List<Job> listaTrabajos = jobRepository.findAll();
        List<Department> listaDepartamentos = departmentRepository.findAll();
        List<ManagerDto> listaManagers = employeesRepository.buscarManager();

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            model.addAttribute("employee",employee);
            model.addAttribute("listaTrabajos",listaTrabajos);
            model.addAttribute("listaDepartamentos",listaDepartamentos);
            model.addAttribute("listaManagers",listaManagers);
            return "employee/editFrm";
        } else {
            return "redirect:/empleado/listar";
        }





    }

    @GetMapping("borrar")
    public String borrarEmpleado(@RequestParam ("id") Integer EmpId) {


        return "redirect:empleado/listar";
    }



    @PostMapping("/buscar")
    public String buscarTransportistaPorNombre(@RequestParam("textoBuscar") String textoBuscar,
                                               Model model) {


        List<EmployeeDto> listaempleados = employeesRepository.buscarEmpleado(textoBuscar);
        model.addAttribute("listaempleados", listaempleados);
        model.addAttribute("textoBuscado", textoBuscar);

        return "employee/listar";
    }


    @PostMapping("/guardar2")
    public String guardarEmployee(@RequestParam("firstName") String firstName,
                                  @RequestParam("lastName") String lastName,
                                  @RequestParam("email") String email,
                                  @RequestParam("jobid") String jobid,
                                  @RequestParam("salary") Integer salary,
                                  @RequestParam("managerid") Integer managerid,
                                  @RequestParam("departmentid") Integer departmentid,
                                  @RequestParam("phone_number") String phone_number,
                                  RedirectAttributes attr){


        employeesRepository.guardaremployee2(firstName,lastName,email,jobid,salary,managerid,departmentid,phone_number);
        attr.addFlashAttribute("msg","empleado guardado");

        return "redirect:/empleado/listar";

    }




}
