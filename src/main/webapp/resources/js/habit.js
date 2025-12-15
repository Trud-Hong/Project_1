function toggleTimeInput(mealType) {
    const checkbox = document.getElementById(mealType);
    const timeInput = document.getElementById(mealType + 'Time');
    timeInput.disabled = !checkbox.checked;
    timeInput.style.opacity = checkbox.checked ? '1' : '0.5';
}

// 초기 상태
window.onload = function() {
    toggleTimeInput('breakfast');
    toggleTimeInput('lunch');
    toggleTimeInput('dinner');
};