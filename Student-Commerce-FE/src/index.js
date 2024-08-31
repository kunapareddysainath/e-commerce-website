import React from 'react';
import ReactDOM from 'react-dom/client';
import '../node_modules/font-awesome/css/font-awesome.min.css';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { Home, Product, Products, AboutPage, ContactPage, Cart, Login, Register, Checkout, PageNotFound,Orders ,Profile} from "./pages"
import {AdminHome,AdminOrders,RegisterEmployee,AdminLogin,Employees} from "./admin-pages";
import DeliveryLayout from './pages/DeliveryLayout';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <BrowserRouter>
    <Routes>
      <Route path="/home" element={<Home />} />
      <Route path="/product" element={<Products />} />
      <Route path="/product/:id" element={<Product />} />
      <Route path="/about" element={<AboutPage />} />
      <Route path="/contact" element={<ContactPage />} />
      <Route path="/cart" element={<Cart />} />
      <Route path="/" element={<Login />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/checkout" element={<Checkout />} />
      <Route path='/orders' element={<Orders />} />
      <Route path="*" element={<PageNotFound />} />
      <Route path="/pagenotfound" element={<PageNotFound />} />
      <Route path="/profile" element={<Profile />} />
      <Route path="/admin-signin" element={<AdminLogin />} />
      <Route path='/admin-home' element={<AdminHome />} />
      <Route path='/admin-register' element={<RegisterEmployee />} />
      <Route path='/admin-employees' element={<Employees />} />
      <Route path='/admin-orders' element={<AdminOrders />} />
      <Route path='//delivery-home' element={<DeliveryLayout/>}/>
    </Routes>
  </BrowserRouter>
);