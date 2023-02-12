function closeAllSelect(elmnt) {//função que fecha todas as boxes abertas
	let x, y, i, xl, yl, arrNo = [];
	x = document.getElementsByClassName("select-items");
	y = document.getElementsByClassName("select-selected");
	xl = x.length;
	yl = y.length;
	for (i = 0; i < yl; i++) {
		if (elmnt === y[i]) {
			arrNo.push(i);
		} else {
			y[i].classList.remove("select-arrow-active");
		}
	}
	for (i = 0; i < xl; i++) {
		if (arrNo.indexOf(i)) {
			x[i].classList.add("select-hide");
		}
	}
}
function selectorDistrict() {
	let sel = document.getElementById("district-name2");
	let sel2 = document.getElementById("teste");
	sel.addEventListener("click", function(e) {
		/*when the select box is clicked, close any other select boxes,
		 and open/close the current select box:*/
		e.stopPropagation();
		closeAllSelect(this);
		sel2.classList.toggle("select-hide");
		this.classList.toggle("select-arrow-active");
	});

	let x = sel2.childElementCount;

	for (let i = 0; i < x; i++) {
		let index = i + 1;
		let elemento = document.getElementById("d" + index);
		elemento.addEventListener("click", function(e) {
			/*when an item is clicked, update the original select box,
			 and the selected item:*/
			let y, i, k, s, h, sl, yl;
			s = document.getElementById("district-name");
			sl = s.length;

			h = sel;
			for (i = 0; i < sl; i++) {
				if (s.options[i].innerHTML === this.innerHTML) {
					s.selectedIndex = i;
					h.value = this.innerHTML;
					y = sel2.getElementsByClassName("same-as-selected");
					yl = y.length;
					for (k = 0; k < yl; k++) {
						y[k].removeAttribute("class");
					}
					this.setAttribute("class", "same-as-selected");
					break;
				}
			}
			h.click();
		});
	}
}