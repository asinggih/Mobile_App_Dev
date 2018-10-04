//
//  examListVC.swift
//  Student Management
//
//  Created by Aditya Singgih on 4/10/18.
//  Copyright © 2018 Aditya Singgih. All rights reserved.
//

import UIKit

public class ExamCell: UITableViewCell{
    
    @IBOutlet weak var examTitle: UILabel!
    @IBOutlet weak var examDate: UILabel!
    @IBOutlet weak var examTime: UILabel!
    
    
}

class examListVC: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}


extension examListVC: UITableViewDelegate{
    
}

extension examListVC: UITableViewDataSource{
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        <#code#>
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        <#code#>
    }
    
    
}
