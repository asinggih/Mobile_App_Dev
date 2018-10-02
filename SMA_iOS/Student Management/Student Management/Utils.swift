//
//  Utils.swift
//  Student Management
//
//  Created by Aditya Singgih on 2/10/18.
//  Copyright © 2018 Aditya Singgih. All rights reserved.
//

import Foundation

extension String
{
    func strip() -> String
    {
        return self.trimmingCharacters(in: .whitespacesAndNewlines)
    }
}

