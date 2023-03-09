const calcWidth = (elementNode) => {
    const element = document.querySelector(elementNode);
    const width = `${window.innerWidth}px`;
    element.style.width = width;
    element.previousSibling.style.width = width;
};

const salir = () => {
    const element = document.querySelector('.logoutMenu');
    element.addEventListener("click",() => {
        window.location.href = "/descarga/inicio.zul";
    });
};

(() => {
    zk.afterMount(function () {
        
       
    });
})();
