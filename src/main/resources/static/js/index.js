var app = angular.module('petStoreApp', [ 'ngMessages', 'ngToast' ]);

app.controller('petStoreController', ['$scope', '$http', 'ngToast',
	function($scope, $http, ngToast) {
		$scope.petToAdd = {
			"id" : null,
			"name" : null,
			"category" : null,
			"tags" : null,
			"pictureLinks" : null,
			"status" : null
		}

		$scope.searchPetId = null;
		$scope.selectedPet = null;
		
		$scope.loadCategory = function() {
			$http({
				url : '/category',
				method : 'GET',
				cache: true,
				params : {}
			}).then(function(response) {
				$scope.categoryOptions = response.data;
				$scope.petToAdd.category = $scope.categoryOptions[0];
			}, function(response) {
				if (response.status == 500) {
					ngToast.create({
						content : "Could not load pet categories. Contact support.",
						className : 'danger'
					});
				} else {
					ngToast.create({
						content : "Something doesn't look right. Contact support.",
						className : 'danger'
					});
				}
			});
		};

		$scope.loadTag = function() {
			$http({
				url : '/tag',
				method : 'GET',
				cache: true,
				params : {}
			}).then(function(response) {
				$scope.tagOptions = response.data;
			}, function(response) {
				if (response.status == 500) {
					ngToast.create({
						content : "Could not load pet categories. Contact support.",
						className : 'danger'
					});
				} else {
					ngToast.create({
						content : "Something doesn't look right. Contact support.",
						className : 'danger'
					});
				}
			});
		};

		$scope.statusOptions = [ 'Available', 'Pending', 'Sold' ];
		$scope.petToAdd.category = $scope.statusOptions[0];

		// ADD pet
		$scope.addPetToStore = function() {
			if (!document.getElementById("addPetForm").elements["addPetName"].checkValidity()) {
				ngToast.create({
					content : "You need to give a good name, to create a pet.",
					className : 'danger'
				});
				return;
			}

			if (!document.getElementById("addPetForm").elements["addPetPhotoUrls"].checkValidity()) {
				ngToast.create({
					content : "Your missing some pictures.",
					className : 'danger'
				});
				return;
			}

			$http({
				url : '/pet',
				method : 'POST',
				data : $scope.petToAdd,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).then(function(response) {
				$scope.selectedPet = response.data;
				$scope.searchPetId = $scope.selectedPet.id;

				$scope.petToAdd.id = null;
				$scope.petToAdd.name = null;
				$scope.petToAdd.category = null;
				$scope.petToAdd.tags = null;
				$scope.petToAdd.pictureLinks = null;
				$scope.petToAdd.status = null;

				$('#addPetModal').modal('hide');

				ngToast.create({
					content : "Pet was successfully created",
					className : 'success'
				});
			}, function(response) {
				if (response.status == 405) {
					$scope.selectedPet = null;
					ngToast.create({
						content : "Invalid input, please check your values",
						className : 'danger'
					});
				} else {
					$scope.selectedPet = null;
					$('#addPetModal').modal('hide');
					ngToast.create({
						content : "Something doesn't look right. Contact support.",
						className : 'danger'
					});
				}
			});
		};

		 // SEARCH for pet
		 $scope.searchPetById = function() {
			 $http({
				 url : '/pet/' + $scope.searchPetId,
				 method : 'GET',
				 params : {}
			 }).then(function(response) {
				 $scope.selectedPet = response.data;
				 $scope.searchPetId = $scope.selectedPet.id;
			 }, function(response) {
				 if (response.status == 400) {
					 $scope.selectedPet = null;
					 ngToast.create({
						 content : "Something doesn't look right. Make sure your ID is correct.",
						 className : 'danger'
					 });
				 } else if (response.status == 404) {
					 $scope.selectedPet = null;
					 ngToast.create({
						 content : "The pet was not listed in the store.",
						 className : 'warning'
					 });
				 } else {
					 ngToast.create({
						 content : "Something doesn't look right. Contact support.",
						 className : 'danger'
					 });
				 }
			 });
		 };

		// DELETE current pet
		 $scope.deleteCurrentPet = function() {
			 $http({
				 url : '/pet/' + $scope.selectedPet.id,
				 method : 'DELETE',
				 params : {}
			 }).then(function(response) {
				 $scope.selectedPet = null;
				 $scope.searchPetId = null;
				 ngToast.create({
					 content : "The pet was deleted from the store.",
					 className : 'success'
				 });
			 }, function(response) {
				 if (response.status == 400) {
					 ngToast.create({
						 content : "Something doesn't look right. Make sure your ID is correct.",
						 className : 'danger'
					 });
				 } else if (response.status == 404) {
					 ngToast.create({
						 content : "The pet was not in the store. Could not delete Pet.",
						 className : 'warning'
					 });
				 } else {
					 ngToast.create({
						 content : "Something doesn't look right. Contact support.",
						 className : 'danger'
					 });
				 }
			 });
		 };

	}
]);