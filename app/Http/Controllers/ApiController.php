<?php
namespace App\Http\Controllers;
use App\Http\Controllers\Controller;
use Illuminate\Foundation\Auth\AuthenticatesUsers;
use Illuminate\Http\Request;
use Auth;
use App\Role;
use DB;
use App\User;
use Response;

class ApiController extends Controller {
	
    public function login(Request $request) {
            $auth = Auth::guard('web')->attempt(['email'=>$request->username,'password'=>$request->password]);
            if(!empty($auth)) {			
                return Response::json([
                "message" => "Successful Login",
                "error" => false,
                "result" => ['user_id' => Auth::user()->id, 'role_id' => Auth::user()->role_id]]);
            } else {
                return Response::json([
                "message" => "Wrong username or password",
                "error" => true,
                "result" => null]);
            }
    }
		// $url = 'https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&key=AIzaSyAkWxugoc7HVf8pUvA4SEEyNJVHsqAj6MY';
		// $client = new \Guzzle\Service\Client($url);
		// return Response::json([
		//   "message" => "",
		//   "error" => false,
		//    "result" => Response::json([$client->getBody()])
		// ]);
    public function getItems(Request $request){
        $items = DB::table('items')
        ->join('users', 'users.id', '=', 'items.owner_id')
        ->where('items.owner_id', '=', $request->user_id)
        ->get();
        // dd(json_encode($items));
        return Response::json([
            "message" => "success",
            "error" => false,
            "result" => ($items)
            ]);
    }
		
    public function postItemUser(Request $request){

        $request = json_decode($request->item);
        // dd($request);
        // $request->name = 'Test Name';
        // $request->description = 'Test descript';
        $result = DB::table('items')->insert([
		    [
				'owner_id' => $request->owner_id, 
				'weight' => $request->weight, 
				'box_length' => $request->box_length, 
				'box_width' => $request->box_width,
                'box_height' => $request->box_height,
                'est_value' => $request->est_value,
                'fragile' => $request->fragile,
                'remarks' => $request->remarks,
                'country_code' => $request->country_code,
                // 'name' => $request->name,
                // 'description'=> $request->description,
                'img_path' => $request->img_path,
			]
			]);
    }

    public function postTripUser(Request $request){

        $request = json_decode($request->trip);
        // dd($request);
        $result = DB::table('trips')->insert([
		    [
				'trip_type' => $request->trip_type, 
				'country_code' => $request->country_code, 
				'description' => $request->description, 
				'arrival_time' => $request->arrival_time,
	            'departure_time' => $request->departure_time,
	            'stay_location' => $request->stay_location,
	            'stay_duration' => $request->stay_duration,
                'city' => $request->city,
                'destination_name' => $request->destination_name,
                'user_id' => $request->user_id,
			]
			]);
    }
    public function getITemTripCourier(Request $request){
        $trip = DB::table('trips')->where([
            ['trips.status', '=', 0],
            ['trips.user_id', '=', $request->user_id]
        ])->first();
        $result = [];
        $item = [];
        if($trip){
            $item = DB::table('items')->where([
                ['items.country_code', '=', $trip->country_code],
                ['items.status', '=',0]
                ])->get();
            // $result = [
            //     'item' => $item,
            //     '$trip' => $trip
            // ];
            $message="";
            // $isError=0;
        } else{
            $message = "No trips found. Please add your trip first";
            // $isError = 1;
        }

        return Response::json([
                "message" => $message,
                "error" => false,
                "result" => $item
        ]);

    }

		
    public function getTransaction(){
        //Owner perspective, park muna to
        $result = DB::table('transaction')->insert([
            [
                'owner_id' => $request->owner_id, 
                'weight' => $request->trip_id, 
                'box_length' => $request->lat, 
                'box_width' => $request->lng,
                                'box_height' => $request->lng,
                                'est_value' => $request->est_value,
                                'fragile' => $request->fragile,
                                'remarks' => $request->remarks
            ]
        ]);
    }

    public function getItemsByCountryCode(Request $request) {
        //Pass country_code and User_id
        //Fetch niya yung mga items with the given country code
        $items = DB::table('items')
            ->join('users', 'items.owner_id', '=', 'users.id')
            ->where([
                    ['items.owner_id', '=', $request->user_id],
                    ['items.country_code', '=', $request->country_code]
            ])
            ->get();

        return Response::json([
            "message" => "success",
            "error" => false,
            "result" => json_encode($items)
        ]);
    }

    // transaction_id, trip_id, liked, shown_item_id, is_swiped, created_at, updated_at
    public function getTripsThatLikedItem(Request $request){
        //Owner side passs ka dito ng item_id, fetch niya yung mga trips na naka liked kay item.
        // liked - 1 = accepted
        // liked - 2 = not accepted
        $trips = DB::table('transactions')
            ->join('trips', 'trips.trip_id', '=', 'transactions.trip_id')
            ->join('users', 'users.id', '=', 'trips.user_id')
            ->where([
                ['shown_item_id', '=', $request->item_id],
                ['liked', '=', 1]
            ])
            ->get();

        return Response::json([
            "message" => "success",
            "error" => false,
            "result" => json_encode($trips)
        ]);
    }

    public function ownerAcceptRequest(Request $request){
        //Owner side passs ka dito ng item_id, fetch niya yung mga trips na naka liked kay item.

        $trips = DB::table('transactions')
            ->where([
                ['transactions.transaction_id', '=', $request->transaction_id]
            ])
            ->update(['liked' => 1]);

        
        if($trips){
            $notaccepted = DB::table('transactions')
            ->where('transactions.shown_item_id', '=', $request->item_id)
            ->where('transactions.transaction_id', '!=', $request->transaction_id)
            ->update(['liked' => 2]);
        }
        return Response::json([
            "message" => "success",
            "error" => false
        ]);
    }
    
}