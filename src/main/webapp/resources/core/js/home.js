document.addEventListener('DOMContentLoaded', function() {
    const heroSlider = document.querySelector('.hero-slider');
    const bookSlider = document.querySelector('.book-slider');

    function startCarousel(slider) {
        let slides = slider.querySelectorAll('.slide');
        let currentSlide = 0;

        function nextSlide() {
            slides[currentSlide].classList.remove('active');
            currentSlide = (currentSlide + 1) % slides.length;
            slides[currentSlide].classList.add('active');
        }

        setInterval(nextSlide, 3000); // Cambia de slide cada 3 segundos
    }

    startCarousel(heroSlider);
    startCarousel(bookSlider);
});