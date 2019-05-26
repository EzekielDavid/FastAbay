<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});


Route::post('/m/location/send', 'ApiController@sendLocation');
Route::post('/m/login', 'ApiController@login');

Route::post('/m/getItems', 'ApiController@getItems');
Route::post('/m/postItemUser', 'ApiController@postItemUser');
Route::post('/m/items/get', 'ApiController@getItems');
Route::post('/m/trip/start', 'ApiController@startTrip');
Route::post('/m/trip/complete', 'ApiController@completeTrip');
Route::post('/m/getItemsByCountryCode', 'ApiController@getItemsByCountryCode');
Route::post('/m/getTripsThatLikedItem', 'ApiController@getTripsThatLikedItem');
Route::post('/m/postTripUser', 'ApiController@postTripUser');
Route::post('/m/getITemTripCourier', 'ApiController@getITemTripCourier');
Route::post('/m/ownerAcceptRequest', 'ApiController@ownerAcceptRequest');
