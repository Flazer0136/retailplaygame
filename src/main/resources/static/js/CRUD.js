// Handle Add Product Button Click
document.getElementById('addProductBtn').addEventListener('click', function() {
    const modalContent = `
        <div class="modal-header">
            <h5 class="modal-title">Add New Product</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
        </div>
        <form id="addProductForm">
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-6">
                        <div class="mb-3">
                            <label class="form-label">Product Name*</label>
                            <input type="text" name="productName" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Price*</label>
                            <input type="number" step="0.01" name="price" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Quantity*</label>
                            <input type="number" name="quantity" class="form-control" required>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="mb-3">
                            <label class="form-label">Console*</label>
                            <input type="text" name="console" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Genre*</label>
                            <input type="text" name="genre" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Description</label>
                            <textarea name="info" class="form-control"></textarea>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                <button type="submit" class="btn btn-primary">Save Product</button>
            </div>
        </form>
    `;

    document.querySelector('#addProductModal .modal-content').innerHTML = modalContent;

    // Initialize the modal
    const addProductModal = new bootstrap.Modal('#addProductModal');
    addProductModal.show();

    // Set up form submission handler
    document.getElementById('addProductForm').addEventListener('submit', async function(e) {
        e.preventDefault();

        try {
            const formData = new FormData(this);
            const productData = {
                productName: formData.get('productName'),
                price: parseFloat(formData.get('price')),
                quantity: parseInt(formData.get('quantity')),
                console: formData.get('console'),
                genre: formData.get('genre'),
                info: formData.get('info')
            };

            const response = await fetch('/api/products', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(productData)
            });

            if (response.ok) {
                addProductModal.hide();
                window.location.reload();
            } else {
                const error = await response.json();
                throw new Error(error.message || 'Failed to create product');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('Error creating product: ' + error.message);
        }
    });
});

// Load product data into edit modal
async function loadEditForm(productId) {
    console.log('Attempting to load product:', productId); // Debug log
    try {
        const response = await fetch(`/api/products/${productId}`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const product = await response.json();
        console.log('Product data:', product); // Debug
        document.getElementById('editModalBody').innerHTML = `
            <div class="row">
                <div class="col-md-6">
                    <div class="mb-3">
                        <label class="form-label">Product Name*</label>
                        <input type="text" name="productName" class="form-control"
                               value="${escapeHtml(product.productName)}" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Price*</label>
                        <input type="number" step="0.01" name="price" class="form-control"
                               value="${product.price}" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Quantity*</label>
                        <input type="number" name="quantity" class="form-control"
                               value="${product.quantity}" required>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="mb-3">
                        <label class="form-label">Console*</label>
                        <input type="text" name="console" class="form-control"
                               value="${escapeHtml(product.console)}" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Genre*</label>
                        <input type="text" name="genre" class="form-control"
                               value="${escapeHtml(product.genre)}" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Description</label>
                        <textarea name="info" class="form-control">${escapeHtml(product.info || '')}</textarea>
                    </div>
                </div>
            </div>
        `;

        document.getElementById('editProductForm').action = `/api/products/${productId}`;
        var editModal = new bootstrap.Modal(document.getElementById('editProductModal'));
        editModal.show();
    } catch (error) {
        console.error('Error loading product:', error);
        alert('Error loading product data: ' + error.message);
    }
}

// Handle edit button clicks
document.addEventListener('click', function(e) {
    const editBtn = e.target.closest('.edit-btn');
    if (editBtn) {
        loadEditForm(editBtn.dataset.productId);
    }

    const deleteBtn = e.target.closest('.delete-btn');
    if (deleteBtn) {
        deleteProduct(deleteBtn.dataset.productId);
    }
});

// Handle Edit Form Submission
document.getElementById('editProductForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    try {
        const formData = new FormData(this);
        const productData = {
            productName: formData.get('productName'),
            price: parseFloat(formData.get('price')),
            quantity: parseInt(formData.get('quantity')),
            console: formData.get('console'),
            genre: formData.get('genre'),
            info: formData.get('info')
        };

        const response = await fetch(this.action, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(productData)
        });

        if (response.ok) {
            $('#editProductModal').modal('hide');
            window.location.reload();
        } else {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Update failed');
        }
    } catch (error) {
        console.error('Error updating product:', error);
        alert('Error updating product: ' + error.message);
    }
});

// Simple HTML escaping function
function escapeHtml(unsafe) {
    if (!unsafe) return '';
    return unsafe.toString()
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}

// Delete Product function
function deleteProduct(id) {
    if (confirm("Are you sure you want to delete this product?")) {
        fetch(`/api/products/${id}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    location.reload();
                } else {
                    throw new Error('Delete failed');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error deleting product');
            });
    }
}
