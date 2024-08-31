import React from 'react';
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import Navbar from '../admin-components/Navbar';
import { Footer } from '../components';

const Home = () => {
  const navigate = useNavigate();

  const handleClick = (path) => {
    navigate(`/admin-${path}`);
  };

  const adminOptions = [
    { "name": "Hire Employee", "color": "#00BFFF", "pageName": "employee-register", "path":"register"  },
    { "name": "List Of Employees", "color": "#FFD700", "pageName": "employees","path":"employees" },
    { "name": "Orders", "color": "#00FF00", "pageName": "orders","path":"orders" },
  ];

  return (
    <>
    <Navbar/>
    <div className="container my-5 py-5 ">
      <div className="row d-flex justify-content-center">
        {adminOptions.map(({ path, name, color }) => (
          <div key={name} className="col-md-4 mb-4">
            <div
              className="card h-100"
              onClick={() => handleClick(path)}
              style={{
                cursor: 'pointer',
                backgroundColor: color,
                borderRadius: '0px 25px',
                border: 'none',
                boxShadow: 'none'
              }}
            >
              <div className="card-body d-flex align-items-center justify-content-center text-white">
                <h5 className="card-title text-center">{name}</h5>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
    <Footer/>
    </>
  );
};

export default Home;
