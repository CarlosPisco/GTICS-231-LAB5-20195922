package com.example.laboratorio5.Repository;



import com.example.laboratorio5.Entity.Employee;
import com.example.laboratorio5.dto.EmployeeDto;
import com.example.laboratorio5.dto.ManagerDto;
import com.example.laboratorio5.dto.ReporteDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface EmployeesRepository extends JpaRepository<Employee,Integer> {


    @Query(value = "select e.employee_id as `id`,\n" +
            "    e.first_name as `nombre`,\n" +
            "\t\t\te.last_name as `apellido`,\n" +
            "\t\t\tj.job_title as `puesto`,\n" +
            "\t\t\td.department_name as `departamento`,\n" +
            "\t\t\tl.city as `ciudad`\n" +
            "\t from employees e\n" +
            "\t inner join jobs j on j.job_id = e.job_id\n" +
            "\tinner join departments d on d.department_id = e.department_id\n" +
            "\tinner join locations l on l.location_id = d.location_id", nativeQuery = true)
    List<EmployeeDto> listarEmpleados();

    @Query(value = "select\n" +
            "        e.employee_id as `id`,\n" +
            "        e.first_name as `nombre`,\n" +
            "        e.last_name as `apellido`,\n" +
            "        j.job_title as `puesto`,\n" +
            "        d.department_name as `departamento`,\n" +
            "        l.city as `ciudad`   \n" +
            "    from\n" +
            "        employees e   \n" +
            "    inner join\n" +
            "        jobs j \n" +
            "            on j.job_id = e.job_id  \n" +
            "    inner join\n" +
            "        departments d \n" +
            "            on d.department_id = e.department_id  \n" +
            "    inner join\n" +
            "        locations l \n" +
            "            on l.location_id = d.location_id     \n" +
            "    where\n" +
            "        LOWER(e.first_name) like %?1% or LOWER(e.last_name) like %?1% or LOWER(j.job_title) like %?1% or LOWER(d.department_name) like %?1%", nativeQuery = true)
    List<EmployeeDto> buscarEmpleado(String textoBuscar);


    @Query(value = "select  distinct(CONCAT(m.first_name,\" \",m.last_name)) as `fullname`,m.employee_id as `id`\n" +
            "from employees e \n" +
            "inner join employees m on m.employee_id = e.manager_id", nativeQuery = true)
    List<ManagerDto> buscarManager();


    @Transactional
    @Modifying
    @Query(value = "UPDATE employees\n" +
            "SET first_name = ?1, Last_name = ?2, email = ?3, job_id = ?4 , salary=?5, manager_id = ?6,department_id = ?7\n" +
            "WHERE employee_id = ?8", nativeQuery = true)
    void guardaremployee(String firstName, String lastName,String email, String jobid, Double salary,Integer managerid, Integer departmentid, Integer employeeid);

    @Transactional
    @Modifying
    @Query(value="INSERT INTO employees\n" +
            "(first_name, last_name, email, job_id, salary, manager_id, department_id,phone_number) \n" +
            "VALUES \n" +
            "(?1,?2,?3,?4,?5,?6,?7,?8);",nativeQuery = true)
    void guardaremployee2 (String first_name, String last_name,String email, String job_id, Integer salary, Integer manager_id , Integer department_id,String phone_number );


    @Query(value="select j.job_title as `Puesto`, count(employee_id) as `NumEmpleados`,\n" +
            "\t\tSUM(salary) as `SumaSalarios`,\n" +
            "        MIN(salary) as `SueldoMinimo`,\n" +
            "        MAX(salary) as `SueldoMaximo`,\n" +
            "        truncate(avg(Salary),2) as `SueldoPromedio`\n" +
            "        from employees e\n" +
            "        inner join jobs j on j.job_id = e.job_id\n" +
            "        group by e.job_id",nativeQuery = true)
    List<ReporteDto> listaReporte();


}
