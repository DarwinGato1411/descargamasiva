const calcWidth = (elementNode) => {
    const element = document.querySelector(elementNode);
    const width = `${window.innerWidth}px`;
    element.style.width = width;
    element.previousSibling.style.width = width;
};

const salir = () => {
    const element = document.querySelector('.logoutMenu');
    element.addEventListener("click", () => {
        window.location.href = "/descarga/inicio.zul";
    });
};

const modal = () => {
    const element = document.querySelector('.info');
    const modal = document.querySelector('.submenu');
    element.addEventListener("click", () => {
        const el = element.getBoundingClientRect();
        console.log(el.height)
        modal.style.bottom = `${el.bottom }px`;
        modal.style.left = `${el.left}px`;
        modal.style.right = `${el.right}px`;

        console.log(modal);


    })
}

(() => {
    zk.afterMount(function () {

       

    });
})();

