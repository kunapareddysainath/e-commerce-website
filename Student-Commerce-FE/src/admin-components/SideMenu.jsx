import React from 'react';
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'font-awesome/css/font-awesome.min.css'; // Import Font Awesome CSS

const SideMenu = () => {
  const navigate = useNavigate();

  const handleClick = (path) => {
    navigate(`/admin-${path}`);
  };

  const adminOptions = [
    { name: "Home", icon: "home", path: "home" },
    { name: "Hire Employee", icon: "user-plus", path: "register" },
    { name: "List Of Employees", icon: "users", path: "employees" },
    { name: "Orders", icon: "fa-solid fa-list", path: "orders" }, // Using FontAwesome icon class names
  ];
  
  return (
    <div className="container-fluid h-100 d-flex">
      <div className="list-group h-100 border-end">
        {adminOptions.map(({ path, name, icon }) => (
          <div
            key={name}
            className="list-group-item list-group-item-action border-0"
            onClick={() => handleClick(path)}
            style={{
              marginBottom: '10px',
              cursor: 'pointer',
              display: 'flex',
              alignItems: 'center'
            }}
          >
            <i className={`fa fa-${icon} me-3`} style={{ fontSize: '2rem', color:"#005b96" }}></i>
            {name}
          </div>
        ))}
      </div>
      <div className="flex-grow-1 p-3">
        {/* Other content goes here */}
      </div>
    </div>
  );
};

export default SideMenu;
