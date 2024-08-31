import React from 'react';
import { NavLink, useNavigate } from 'react-router-dom';

const Navbar = () => {
    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.clear();
        navigate("/");
    }

    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-light py-3 sticky-top">
            <div className="container">
                <NavLink className="navbar-brand fw-bold fs-4 px-2" to="/"><i>Student Ecommerce</i></NavLink>
                <h5 className="mb-0 font-italic fw-bold fs-4">Admin Dashboard</h5>
                <button className='btn btn-outline-dark m-2' onClick={handleLogout}>
                    <i className="fa fa-sign-out mr-1" />
                    Log out
                </button>
            </div>
        </nav>
    );
}

export default Navbar;
