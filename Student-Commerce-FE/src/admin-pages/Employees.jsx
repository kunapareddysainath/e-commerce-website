import React, { useEffect, useState } from 'react'
import axios from 'axios';
import { backEndUrl } from '../config';
import Navbar from '../admin-components/Navbar';
import SideMenu from '../admin-components/SideMenu';
import { Footer } from '../components';

const Employees = () => {

    const [employees, setEmployees] = useState([]);

    const fetchEmployees = async () => {
        const authToken = localStorage.getItem("authToken");

        try {
            const response = await axios.get(
                `${backEndUrl}/api/employee/getEmployees`,
                {
                    headers: {
                        'Authorization': `Bearer ${authToken}` // Include authToken in headers
                    }
                }
            );
            setEmployees(response.data);
            // Handle the response if needed
            console.log(response.data);
        } catch (error) {
            // Handle the error if needed
        }
    };

    useEffect(() => {
        fetchEmployees();
    }, []);

    return (
        <>
            <Navbar />
            <div className='container-fluid'>
                <div className='row'>
                    <div className="col-md-2">
                        <SideMenu />
                    </div>
                    <div className="col-md-10">
                        <div className="container mt-5">
                            <div className="card">
                                <div className="card-header">
                                    <h3>Employee List</h3>
                                </div>
                                <div className="card-body">
                                    <table className="table table-striped">
                                        <thead>
                                            <tr>
                                                <th>Name</th>
                                                <th>Username</th>
                                                <th>Phone Number</th>
                                                <th>Email Address</th>
                                                <th>Role</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {employees.map((employee, index) => (
                                                <tr key={index}>
                                                    <td>{employee.name}</td>
                                                    <td>{employee.username}</td>
                                                    <td>{employee.phoneNumber}</td>
                                                    <td>{employee.emailAddress}</td>
                                                    <td>{employee.role.label}</td>
                                                </tr>
                                            ))}
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <Footer/>
        </>
    )
}

export default Employees;
