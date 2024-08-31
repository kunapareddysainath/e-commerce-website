import React from "react";
import ReactLoading from "react-loading";

const Loading = () => {
    return (
        <div className="d-flex justify-content-center align-items-center position-fixed w-100 h-100 bg-dark bg-opacity-50">
            <div className="bg-white p-4 rounded shadow">
                <ReactLoading type="spin" color="#0000FF" height={100} width={100} />
            </div>
        </div>
    );
}

export default Loading;
